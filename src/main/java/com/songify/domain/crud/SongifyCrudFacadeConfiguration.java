package com.songify.domain.crud;

public class SongifyCrudFacadeConfiguration {

    public static SongifyCrudFacade createSongifyCrudFacade(
            final SongRepository songRepository,
            final ArtistRepository artistRepository,
            final GenreRepository genreRepository,
            final AlbumRepository albumRepository
    ) {
        SongRetriever songRetriever = new SongRetriever(songRepository);
        SongAdder songAdder = new SongAdder(songRepository);
        GenreDeleter genreDeleter = new GenreDeleter(genreRepository);
        SongUpdater songUpdater = new SongUpdater(songRetriever, songRepository, songAdder);
        SongDeleter songDeleter = new SongDeleter(songRepository, songRetriever, genreDeleter);
        ArtistAdder artistAdder = new ArtistAdder(artistRepository);
        GenreAdder genreAdder = new GenreAdder(genreRepository);
        AlbumAdder albumAdder = new AlbumAdder(songRetriever,albumRepository);
        ArtistRetriever artistRetriever = new ArtistRetriever(artistRepository);
        AlbumRetriever albumRetriever = new AlbumRetriever(albumRepository);
        AlbumDeleter albumDeleter = new AlbumDeleter(albumRepository);
        ArtistDeleter artistDeleter = new ArtistDeleter(artistRepository, artistRetriever, albumRetriever, songDeleter, albumDeleter);
        ArtistAssigner artistAssigner = new ArtistAssigner(artistRetriever, albumRetriever);
        ArtistUpdater artistUpdater = new ArtistUpdater(artistRepository);
        GenreRetriever genreRetriever = new GenreRetriever(genreRepository);

        return new SongifyCrudFacade(
                songRetriever,
                songUpdater,
                songDeleter,
                songAdder,
                artistAdder,
                genreAdder,
                albumAdder,
                artistRetriever,
                albumRetriever,
                artistDeleter,
                artistAssigner,
                artistUpdater,
                genreRetriever
        );
    }
}
