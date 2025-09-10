/*
Author: QuanTuanHuy
Description: Part of Serp Project
*/

package port

import "context"

type IRedisPort interface {
	SetToRedis(ctx context.Context, key string, value any, ttl int) error
	GetFromRedis(ctx context.Context, key string, destination any) error
	SetHSetToRedis(ctx context.Context, key string, mapData map[string]any, ttl int) error
	GetHSetFromRedis(ctx context.Context, key string) (map[string]string, error)
	DeleteKeyFromRedis(ctx context.Context, key string) error
	GetKeysFromRedis(ctx context.Context, pattern string) ([]string, error)
	ExistsInRedis(ctx context.Context, key string) (bool, error)
}
