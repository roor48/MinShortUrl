package study.min.shorturl.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="urls")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShortUrlEntity {
    @Id
    @Column(name = "hash")
    String hash;
    @Column(name = "origin", unique = true, nullable = false, length = 2048)
    String origin;
}
