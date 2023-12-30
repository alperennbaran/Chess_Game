public enum GameStatus {
    ACTIVE,
    BLACK_WIN,
    WHITE_WIN,
    FORFEIT, // Zaman aşımına bağlı yenilgi
    STALEMATE, //  Oyun berabere bitti. (Pat)
    RESIGNATION //  Oyuncu çekildi.
}
