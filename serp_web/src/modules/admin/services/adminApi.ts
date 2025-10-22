/**
 * Author: QuanTuanHuy
 * Description: Part of Serp Project - Admin API barrel exports
 */

// Organizations API
export {
  organizationsApi,
  useGetOrganizationsQuery,
  useGetOrganizationByIdQuery,
  useLazyGetOrganizationsQuery,
  useLazyGetOrganizationByIdQuery,
} from './organizations/organizationsApi';

// Users API
export {
  usersApi,
  useGetUsersQuery,
  useLazyGetUsersQuery,
} from './users/usersApi';

// Subscriptions API
export {
  subscriptionsApi,
  useGetSubscriptionsQuery,
  useGetSubscriptionByIdQuery,
  useLazyGetSubscriptionsQuery,
  useLazyGetSubscriptionByIdQuery,
} from './subscriptions/subscriptionsApi';

// Subscription Plans API
export {
  plansApi,
  useGetSubscriptionPlansQuery,
  useGetSubscriptionPlanByIdQuery,
  useCreateSubscriptionPlanMutation,
  useUpdateSubscriptionPlanMutation,
  useDeleteSubscriptionPlanMutation,
} from './plans/plansApi';

// Modules API
export {
  modulesApi,
  useGetModulesQuery,
  useGetModuleByIdQuery,
  useCreateModuleMutation,
  useUpdateModuleMutation,
} from './modules/modulesApi';
