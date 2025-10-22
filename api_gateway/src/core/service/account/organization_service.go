/*
Author: QuanTuanHuy
Description: Part of Serp Project
*/

package service

import (
	"context"

	"github.com/golibs-starter/golib/log"
	"github.com/serp/api-gateway/src/core/domain/dto/response"
	port "github.com/serp/api-gateway/src/core/port/client/account"
)

type IOrganizationService interface {
	GetOrganizations(ctx context.Context, search *string, status *string, organizationType *string, page *int, pageSize *int) (*response.BaseResponse, error)
	GetOrganizationById(ctx context.Context, organizationID int64) (*response.BaseResponse, error)
	GetMyOrganization(ctx context.Context) (*response.BaseResponse, error)
}

type OrganizationService struct {
	organizationClient port.IOrganizationClientPort
}

func (o *OrganizationService) GetOrganizations(ctx context.Context, search *string, status *string, organizationType *string, page *int, pageSize *int) (*response.BaseResponse, error) {
	res, err := o.organizationClient.GetOrganizations(ctx, search, status, organizationType, page, pageSize)
	if err != nil {
		log.Error(ctx, "OrganizationService: GetOrganizations error: ", err.Error())
		return nil, err
	}
	return res, nil
}

func (o *OrganizationService) GetOrganizationById(ctx context.Context, organizationID int64) (*response.BaseResponse, error) {
	res, err := o.organizationClient.GetOrganizationById(ctx, organizationID)
	if err != nil {
		log.Error(ctx, "OrganizationService: GetOrganizationById error: ", err.Error())
		return nil, err
	}
	return res, nil
}

func (o *OrganizationService) GetMyOrganization(ctx context.Context) (*response.BaseResponse, error) {
	res, err := o.organizationClient.GetMyOrganization(ctx)
	if err != nil {
		log.Error(ctx, "OrganizationService: GetMyOrganization error: ", err.Error())
		return nil, err
	}
	return res, nil
}

func NewOrganizationService(organizationClient port.IOrganizationClientPort) IOrganizationService {
	return &OrganizationService{
		organizationClient: organizationClient,
	}
}
