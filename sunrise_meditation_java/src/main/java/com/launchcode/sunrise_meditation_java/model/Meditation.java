package com.launchcode.sunrise_meditation_java.model;

import javax.persistence.*;

@Entity
@Table(name = "MEDITATION_LOG")
public class Meditation {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "USER_ID")
        private Long userId;

        @Column(name = "ACTION")
        private String action;

        @Column(name = "CREATED_TIMESTAMP")
        private String created_timestamp;

        @Column(name = "TIME_LOG")
        private String time_log;

        @Column(name = "END_TIMESTAMP")
        private int end_timestamp;

    public Meditation() {
    }

    public Meditation(Long userId, String action, String created_timestamp, String time_log, int end_timestamp) {
        this.userId = userId;
        this.action = action;
        this.created_timestamp = created_timestamp;
        this.time_log = time_log;
        this.end_timestamp = end_timestamp;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getCreated_timestamp() {
        return created_timestamp;
    }

    public void setCreated_timestamp(String created_timestamp) {
        this.created_timestamp = created_timestamp;
    }

    public String getTime_log() {
        return time_log;
    }

    public void setTime_log(String time_log) {
        this.time_log = time_log;
    }

    public int getEnd_timestamp() {
        return end_timestamp;
    }

    public void setEnd_timestamp(int end_timestamp) {
        this.end_timestamp = end_timestamp;
    }
}
