/**
 * Authors: QuanTuanHuy
 * Description: Part of Serp Project - Base API configuration for all services
 */

import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';
import type { RootState } from '../store';

const API_BASE_URL =
  process.env.NEXT_PUBLIC_API_BASE_URL || 'http://localhost:8080';

// Base query with authentication
const baseQuery = fetchBaseQuery({
  baseUrl: `${API_BASE_URL}/api/v1`,
  prepareHeaders: (headers, { getState }) => {
    const state = getState() as RootState;
    const token = state.account.auth?.token;

    if (token) {
      headers.set('authorization', `Bearer ${token}`);
    }
    headers.set('content-type', 'application/json');

    return headers;
  },
});

// Base query with error handling and token refresh
const baseQueryWithReauth = async (args: any, api: any, extraOptions: any) => {
  let result = await baseQuery(args, api, extraOptions);

  // Handle 401 unauthorized - token refresh logic
  if (result.error && result.error.status === 401) {
    const state = api.getState() as RootState;
    const refreshToken = state.account.auth?.refreshToken;

    if (refreshToken) {
      const refreshResult = await baseQuery(
        {
          url: '/auth/refresh-token',
          method: 'POST',
          body: { refreshToken },
        },
        api,
        extraOptions
      );

      if (refreshResult.data) {
        // Import setTokens action dynamically to avoid circular imports
        const { setTokens } = await import('@/modules/account/store');
        const tokenData = refreshResult.data as any;

        if (
          tokenData.code === 200 &&
          tokenData.status.toLowerCase() === 'success'
        ) {
          // Store new tokens and retry original request
          api.dispatch(
            setTokens({
              token: tokenData.data.accessToken,
              refreshToken: tokenData.data.refreshToken,
            })
          );

          result = await baseQuery(args, api, extraOptions);
        } else {
          // Refresh response indicates failure
          const { clearAuth } = await import('@/modules/account/store');
          api.dispatch(clearAuth());
        }
      } else {
        // Refresh failed - clear auth state
        const { clearAuth } = await import('@/modules/account/store');
        api.dispatch(clearAuth());
      }
    } else {
      // No refresh token - clear auth state
      const { clearAuth } = await import('@/modules/account/store');
      api.dispatch(clearAuth());
    }
  }

  return result;
};

// Create the main API slice
export const api = createApi({
  reducerPath: 'api',
  baseQuery: baseQueryWithReauth,

  // Tag types for cache invalidation
  tagTypes: [
    'account/user',
    'account/profile',
    'account/auth',
    'account/permissions',
    'account/menus',
    'Customer',
    'Lead',
    'Opportunity',
    'Activity',
    'Analytics',
    // Admin tags
    'admin/Organization',
    'admin/Subscription',
    'admin/Plan',
    'admin/Module',
    'admin/User',
  ],

  // Define endpoints in separate files for each module
  endpoints: () => ({}),
});
