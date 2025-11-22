/**
 * Author: QuanTuanHuy
 * Description: Part of Serp Project
 */

package serp.project.account.core.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class UserDepartmentEntity extends BaseEntity {

    private Long userId;

    private Long departmentId;

    private Boolean isPrimary;

    private String jobTitle;

    private String description;

    private Boolean isActive;
}
