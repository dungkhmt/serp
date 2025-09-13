/*
Author: QuanTuanHuy
Description: Part of Serp Project
*/

package request

type CreateTaskRequest struct {
	Title       string    `json:"title"`
	Description *string   `json:"description"`
	Priority    []*string `json:"priority"`
	Status      *string   `json:"status"`
	StartDate   *int64    `json:"startDate"`
	Deadline    *int64    `json:"deadline"`
	Duration    *float32  `json:"duration"`
	GroupTaskID int64     `json:"groupTaskId"`
}
