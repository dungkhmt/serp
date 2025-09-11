/*
Author: QuanTuanHuy
Description: Part of Serp Project
*/

package message

import "time"

type KafkaMessage struct {
	Cmd          KafkaCommand `json:"cmd"`
	ErrorCode    ErrorCode    `json:"errorCode"`
	ErrorMessage ErrorMsg     `json:"errorMessage"`
	DisplayTime  int64        `json:"displayTime"`
	Data         any          `json:"data"`
	ReplyTopic   string       `json:"replyTopic,omitempty"`
}

func CreateKafkaMessage(cmd KafkaCommand, errorCode ErrorCode, errorMessage ErrorMsg, data any) *KafkaMessage {
	return &KafkaMessage{
		Cmd:          cmd,
		ErrorCode:    errorCode,
		ErrorMessage: errorMessage,
		DisplayTime:  time.Now().UnixMilli(),
		ReplyTopic:   "",
		Data:         data,
	}
}

func CreateKafkaMessageWithReplyTopic(cmd KafkaCommand, errorCode ErrorCode, errorMessage ErrorMsg, data any, replyTopic string) *KafkaMessage {
	return &KafkaMessage{
		Cmd:          cmd,
		ErrorCode:    errorCode,
		ErrorMessage: errorMessage,
		DisplayTime:  time.Now().UnixMilli(),
		ReplyTopic:   replyTopic,
		Data:         data,
	}
}

type KafkaCommand string

const (
	CREATE_TASK          KafkaCommand = "taskManagerCreateTask"
	UPDATE_TASK          KafkaCommand = "taskManagerUpdateTask"
	DELETE_TASK          KafkaCommand = "taskManagerDeleteTask"
	CREATE_SCHEDULE_TASK KafkaCommand = "scheduleManagerCreateScheduleTask"
)

type ErrorCode string

const (
	ErrorCodeSuccess ErrorCode = "00"
	ErrorCodeFailure ErrorCode = "01"
)

type ErrorMsg string

const (
	ErrorMsgSuccess ErrorMsg = "Successful"
	ErrorMsgFailure ErrorMsg = "Failed"
)
