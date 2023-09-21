package com.blanho.todolist.domain;
import com.blanho.todolist.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "todos")
public class ToDoList {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column( name= "deadline", nullable = false)
    private Date duedate;

    @Column( name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    @Column( name = "updated_at")
    @CreationTimestamp
    private Date updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "category_id")
   private Category category;
}
