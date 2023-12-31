package org.zerock.j1.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tbl_todo2")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
public class Todo {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY) // autoIncrement 사용을 위한 코드
    private Long tno;

    @Column(length = 300, nullable = false) // 300자 제한, not null
    private String title;

    public void changeTitle(String title){
        this.title = title;
    }

    
}
