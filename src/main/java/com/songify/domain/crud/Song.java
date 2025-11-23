package com.songify.domain.crud;

import com.songify.domain.crud.util.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        indexes = @Index(
                name = "idx_song_name",
                columnList = "name"
        )
)
class Song extends BaseEntity {

    @Id
    @GeneratedValue(generator = "song_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "song_id_seq",
            sequenceName = "song_id_seq",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    private Instant releaseDate;

    private Long duration;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Genre genre;

    @Enumerated(EnumType.STRING)
    private SongLanguage language;

    public Song(final String name) {
        this.name = name;
    }

    public Song(String name, Instant releaseDate, Long duration, SongLanguage language) {
        this.name = name;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.language = language;
    }
}
