package org.example.domain.schedule.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.user.UserEntity.BaseEntity;
import org.example.domain.user.UserEntity.User;

@Entity
@Table(name = "schedule_participant")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ScheduleParticipant extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Builder
    public ScheduleParticipant(User user, Schedule schedule, String userName) {
        this.user = user;
        this.schedule = schedule;
        this.userName = userName;
    }
}
