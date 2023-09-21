package com.blanho.todolist.domain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "media")
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "mime_type")
    private String mimeType;

    private Integer size;

    private String description;

    @Column(name = "upload_date")
    private Date uploadDate;

    @Column( name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    @Column( name = "updated_at")
    @CreationTimestamp
    private Date updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id")
    private ToDoList todo;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
