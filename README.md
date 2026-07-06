# BlackJack – Java Desktop Card Game

**BlackJack** is a desktop Blackjack game built with **Java** and **Java Swing**, using **SQLite** for persistent user data and balances.

The application lets users create an account, log in, and play Blackjack against a dealer, with real-time balance tracking, a betting system, and a graphical interface for cards and chips.

---

## App Interface

### Login / Register

When the app launches, the user is prompted to either **Login** or **Register**.

- Each username must be unique.
- Password must match the stored username.
- An error is shown if a field is empty or credentials don't match.
- New accounts start with an initial balance of **1000**.
- User data (username, password, balance) is stored in the SQLite database `utilizatori.db`.

<img width="483" height="711" alt="image" src="https://github.com/user-attachments/assets/1624709d-de84-407b-a7f9-fa7c325681f7" />

### Main Screen

After logging in, the user is redirected to the main screen, which shows a central **Play** button to start a new round.

<img width="1264" height="709" alt="image" src="https://github.com/user-attachments/assets/a703bc77-364d-4688-a795-293b99e2fed4" />

### Game Screen

Once in a game:

- The player's and dealer's cards are shown face down initially.
- The player places a bet using the available buttons: **Reset**, **Add 50$**, **Add 100$**, **Add 250$**, and **All In**.
- After betting, the player's cards and one of the dealer's cards are revealed, and the player can choose:
  - **Hit** – draw another card (can be used multiple times, but busting above 21 results in an automatic loss).
  - **Stand** – keep the current hand and pass the turn to the dealer.
- If the player's hand exceeds 21, they lose automatically.
- The dealer plays according to standard Blackjack rules (must hit until reaching at least 17).
- The winner is determined and displayed on screen, and the balance is updated both in the database and on screen.
- After the round ends, the player can start a new round using **Play Again**.



<img width="1260" height="708" alt="image" src="https://github.com/user-attachments/assets/ed0fde29-1c8f-493e-914f-b33fcc8f48d2" />

---

## Main Features

### Authentication & User Registration

- Users log in with a unique username and password.
- New users are registered with an initial balance of 1000.
- Credentials and balance are stored in SQLite.

### Balance Management

- Balance is updated in real time after every win or loss.
- Balance persists between sessions via the database.

### Betting System

- Bet amount is selected using dedicated chip buttons.
- Chips corresponding to the current bet are displayed on screen.
- Balance updates in real time after the bet is placed.

### Blackjack Gameplay

- Cards are dealt after the bet is placed.
- The player's cards are shown face up; only one of the dealer's cards is revealed.
- Player can Hit or Stand.
- Ace values are automatically adjusted (11/1) to avoid busting when possible.
- Dealer plays automatically following standard rules.

---

## Architecture

```
Frontend (Java Swing) → Game Logic (Java) → Database (SQLite)
```

- **Frontend** – Java Swing (`LoginRegister`, `InterfataJoc`)
- **Backend / Game Logic** – Java (`BlackJack`, `Deck`, `Carte`, `Dealer`, `Player`)
- **Database** – SQLite (`DB.java`, `utilizatori.db`)

---

## Project Structure

```
BlackJack/
 ├── Main.java
 ├── BlackJack.java
 ├── Dealer.java
 ├── Player.java
 ├── Deck.java
 ├── Carte.java
 ├── DB.java
 ├── LoginRegister.java
 ├── InterfataJoc.java
 └── utilizatori.db
```

---

## Tech Stack

| Component        | Technology     |
|-------------------|---------------|
| Language          | Java          |
| GUI               | Java Swing    |
| Database          | SQLite        |
| Persistence       | JDBC          |

---

## Database Structure

Table `utilizatori`:

| Column   | Type    | Description          |
|----------|---------|-----------------------|
| id       | INTEGER | Primary key, auto-increment |
| nume     | TEXT    | Username (unique)     |
| parola   | TEXT    | Password              |
| balanta  | INTEGER | User's current balance |

---

## Installation & Setup

### Requirements

- Java (JDK)
- SQLite JDBC driver
- An IDE or `javac`/`java` for compiling and running

### Running the project

Clone the repository:

```bash
git clone https://github.com/LorinczEduardRaoul/blackjack.git
```

Compile and run:

```bash
javac *.java
java Main
```

The database `utilizatori.db` will be created automatically on first run.

---

## Possible Improvements

- Additional customization options for users.
- New game variants or extra gameplay features.
- Local multiplayer support on the same device.
- Player statistics (win rate, leaderboard of top balances).
