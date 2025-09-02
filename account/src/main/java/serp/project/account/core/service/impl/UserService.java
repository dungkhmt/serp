/**
 * Author: QuanTuanHuy
 * Description: Part of Serp Project
 */

package serp.project.account.core.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import serp.project.account.core.domain.constant.Constants;
import serp.project.account.core.domain.dto.request.CreateUserDto;
import serp.project.account.core.domain.dto.request.GetUserParams;
import serp.project.account.core.domain.entity.BaseEntity;
import serp.project.account.core.domain.entity.UserEntity;
import serp.project.account.core.domain.entity.UserRoleEntity;
import serp.project.account.core.exception.AppException;
import serp.project.account.core.port.store.IUserPort;
import serp.project.account.core.port.store.IUserRolePort;
import serp.project.account.core.service.IRoleService;
import serp.project.account.core.service.IUserService;
import serp.project.account.infrastructure.store.mapper.UserMapper;
import serp.project.account.kernel.utils.BcryptPasswordEncoder;
import serp.project.account.kernel.utils.CollectionUtils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserPort userPort;
    private final IUserRolePort userRolePort;

    private final IRoleService roleService;

    private final BcryptPasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserEntity createUser(CreateUserDto request) {
        UserEntity existedUser = userPort.getUserByEmail(request.getEmail());
        if (existedUser != null) {
            throw new AppException(Constants.ErrorMessage.USER_ALREADY_EXISTS);
        }

        UserEntity user = userMapper.createUserMapper(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user = userPort.save(user);
        final long userId = user.getId();

        if (!CollectionUtils.isEmpty(request.getRoleIds())) {
            List<UserRoleEntity> userRoles = request.getRoleIds().stream()
                    .map(roleId -> UserRoleEntity.builder()
                            .userId(userId)
                            .roleId(roleId)
                            .build())
                    .collect(Collectors.toList());
            userRolePort.saveAll(userRoles);
        }
        return user;
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        UserEntity user = userPort.getUserByEmail(email);
        if (user == null) {
            return null;
        }
        var userRoles = userRolePort.getUserRolesByUserId(user.getId());
        var roleMap = roleService.getAllRoles().stream()
                .collect(Collectors.toMap(BaseEntity::getId, Function.identity()));
        user.setRoles(userRoles.stream().map(ur -> roleMap.get(ur.getRoleId())).toList());
        return user;
    }

    @Override
    public Pair<Long, List<UserEntity>> getUsers(GetUserParams params) {
        var result = userPort.getUsers(params);
        if (CollectionUtils.isEmpty(result.getSecond())) {
            return result;
        }

        var users = result.getSecond();
        List<Long> userIds = users.stream()
                .map(UserEntity::getId).toList();
        var userRoles = userRolePort.getUserRolesByUserIds(userIds);
        var roleMap = roleService.getAllRoles().stream()
                .collect(Collectors.toMap(BaseEntity::getId, Function.identity()));

        users.forEach(user -> {
            var roles = userRoles.stream()
                    .filter(userRole -> userRole.getUserId().equals(user.getId()))
                    .map(userRole -> roleMap.get(userRole.getRoleId()))
                    .toList();
            user.setRoles(roles);
        });

        return result;
    }
}
