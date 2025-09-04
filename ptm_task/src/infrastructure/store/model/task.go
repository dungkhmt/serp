/*
Author: QuanTuanHuy
Description: Part of Serp Project
*/

package model

import "time"

type TaskModel struct {
	BaseModel
	Title        string     `gorm:"not null" json:"title"`
	Description  string     `gorm:"not null" json:"description"`
	Priority     string     `gorm:"type:text" json:"priority"`
	Status       string     `gorm:"not null" json:"status"`
	StartDate    *time.Time `json:"startDate"`
	Deadline     *time.Time `json:"deadline"`
	Duration     float64    `gorm:"default:0" json:"duration"`
	ActiveStatus string     `gorm:"default:ACTIVE" json:"activeStatus"`
	GroupTaskID  int64      `gorm:"not null;index" json:"groupTaskId"`
	UserID       int64      `gorm:"not null;index" json:"userId"`
}

func (TaskModel) TableName() string {
	return "tasks"
}
