/*
Author: QuanTuanHuy
Description: Part of Serp Project
*/

package bootstrap

import (
	"log"

	"github.com/serp/ptm-schedule/src/infrastructure/store/model"
	"gorm.io/gorm"
)

func InitializeDB(db *gorm.DB) {
	err := db.AutoMigrate(
		&model.SchedulePlanModel{},
		&model.ScheduleGroupModel{},
		&model.ScheduleTaskModel{},
		&model.TimeBubblesModel{},
		&model.ScheduleDayModel{},
	)
	if err != nil {
		log.Fatal("Failed to run migrations: ", err)
	}
}
