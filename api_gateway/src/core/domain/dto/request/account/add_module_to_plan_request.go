/*
Author: QuanTuanHuy
Description: Part of Serp Project
*/

package request

type AddModuleToPlanRequest struct {
	ModuleId          int64  `json:"moduleId"`
	LicenseType       string `json:"licenseType"`
	IsIncluded        *bool  `json:"isIncluded"`
	MaxUsersPerModule *int   `json:"maxUsersPerModule"`
}
