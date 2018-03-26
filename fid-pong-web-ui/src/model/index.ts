export interface Player {
  id: number;
  firstName: string;
  lastName: string;
  nickname: string;
  email: string;
}

export interface Team {
  player1: Player
  player2: Player
}

export interface Game {
  id: number
  startTime: number
  location: string
  gamePoint: number
}

export interface TwoPlayerGame extends Game{
  playerOne: Player
  playerTwo: Player
  playerOneScore: number
  playerTwoScore: number

}

export class PlayerTools {

   static displayName(p: Player): string {
    return p.nickname ? p.nickname: `${p.lastName}, ${p.firstName}`;
  }
}
