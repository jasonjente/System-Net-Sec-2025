// src/test/java/org/aueb/fair/dice/application/service/game/GameServiceTest.java
package org.aueb.fair.dice.application.service.game;

import org.aueb.fair.dice.application.exception.user.UserAlreadyInGameException;
import org.aueb.fair.dice.application.exception.user.UserNotFoundException;
import org.aueb.fair.dice.application.port.primary.user.UserQueryPort;
import org.aueb.fair.dice.application.port.secondary.game.GamePersistencePort;
import org.aueb.fair.dice.domain.game.GameResult;
import org.aueb.fair.dice.domain.user.User;
import org.aueb.fair.dice.infrastructure.adapter.secondary.security.SHA256HashingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameServiceTest {

    @Mock
    private GamePersistencePort gamePersistencePort;

    @Mock
    private UserQueryPort userQueryPort;

    @InjectMocks
    private GameService gameService;

    private AutoCloseable mocks;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @Test
    void startGame_successfulFlow_returnsHashedString() {
        // Arrange: known user and persisted result
        Long userId = 42L;
        String clientString = "client123";
        User user = new User(userId, "Michael", "Scott", "mgscott",
                "my$up3R$eCuReP@ss", "Michael.G.Scott@dunder-mifflin.com");
        when(userQueryPort.findById(userId)).thenReturn(Optional.of(user));

        // simulate no unfinished games
        when(gamePersistencePort.getGameResultsByUserId(userId))
                .thenReturn(Collections.emptyList());

        // stub create(...) to return a deterministic GameResult
        GameResult persisted = GameResult.builder()
                .user(user)
                .randomClientString(clientString)
                .randomServerString("serverABC")
                .serverGuess(5)
                .build();
        when(gamePersistencePort.create(any(GameResult.class)))
                .thenReturn(persisted);

        // Act
        String hash = gameService.startGame(clientString, userId);

        // Assert: hashing matches SHA256("5" + clientString + "serverABC")
        String expected = new SHA256HashingService()
                .hashConcatenation("5", clientString, "serverABC");
        assertEquals(expected, hash);

        verify(gamePersistencePort).create(argThat(
                gameResult -> clientString.equals(gameResult.getRandomClientString())
                            && gameResult.getUser().equals(user)));
    }

    @Test
    void startGame_userNotFound_throws() {
        when(userQueryPort.findById(1L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class,
                () -> gameService.startGame("any", 1L));
    }

    @Test
    void startGame_userAlreadyInGame_throws() {
        Long userId = 7L;
        User user = new User(userId, "Ron", "Swanson", "RSwan4", "1HateEvery0n3!",
                "ron.swanson@example.com");
        when(userQueryPort.findById(userId)).thenReturn(Optional.of(user));
        when(gamePersistencePort.getGameResultsByUserId(userId))
                .thenReturn(List.of(GameResult.builder().build()));
        assertThrows(UserAlreadyInGameException.class,
                () -> gameService.startGame("foo", userId));
    }

    @Test
    void loadClientGuess_correctGuess_marksWinAndUpdates() {
        // Arrange
        Long userId = 11L;
        GameResult gameResult = GameResult.builder()
                .user(new User(userId, "Ron",
                        "Swanson", "RSwan4", "1HateEvery0n3!", "ron.swanson@example.com"))
                .serverGuess(3)
                .randomClientString("1234567890abcdefg")
                .randomServerString("abcdefg1234567890")
                .build();
        when(gamePersistencePort.getGameResultsByUserId(userId))
                .thenReturn(List.of(gameResult));

        // Act: pass same guess as serverGuess -> win
        GameResult result = gameService.loadClientGuess(3, userId);

        // Assert
        assertTrue(result.isWin());
        assertTrue(result.getEnded());
        verify(gamePersistencePort).update(result);
    }

    @Test
    void loadClientGuess_noGameInProgress_throws() {
        when(gamePersistencePort.getGameResultsByUserId(99L))
                .thenReturn(Collections.emptyList());
        assertThrows(UserAlreadyInGameException.class, () -> gameService.loadClientGuess(1, 99L));
    }
}
