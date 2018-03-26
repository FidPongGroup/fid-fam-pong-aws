import { Component, OnInit } from '@angular/core';
import {Player, PlayerTools, TwoPlayerGame} from "../../model";
import {MatTableDataSource} from "@angular/material";
import {TwoPlayerGameService} from "../two-player-game.service";

@Component({
  selector: 'app-game-list',
  templateUrl: './game-list.component.html',
  styleUrls: ['./game-list.component.scss']
})
export class GameListComponent implements OnInit {

  displayedColumns = ['startTime', 'player1Name', 'player1Score', 'player2Name', 'player2Score', 'location'];
  dataSource = new MatTableDataSource<TwoPlayerGame>(ELEMENT_DATA);
  displayName = PlayerTools.displayName;

  selectedPlayer: Player;

  constructor(private twoPlayerGameService: TwoPlayerGameService) { }

  ngOnInit() {

    this.twoPlayerGameService.getTwoPlayerGames()
      .subscribe()
  }

}

const ELEMENT_DATA: TwoPlayerGame[] = [{
  "id": 1,
  "location": null,
  "startTime": 1451656800000,
  "gamePoint": 11,
  "playerOne": {"firstName": "Other", "lastName": "Josh", "email": "Other.Josh@email.com", "nickname": "OJ", "id": 1},
  "playerTwo": {"firstName": "Alpha", "lastName": "Josh", "email": "Alpha.Josh@email.com", "nickname": "AJ", "id": 2},
  "playerOneScore": 7,
  "playerTwoScore": 0
}, {
  "id": 2,
  "location": "4th Floor",
  "startTime": 1483279200000,
  "gamePoint": 11,
  "playerOne": {"firstName": "Thomas", "lastName": "Jenkins", "email": "t.j@email.com", "nickname": "TJ", "id": 3},
  "playerTwo": {
    "firstName": "Corey",
    "lastName": "Dank-Meme",
    "email": "dank.memes@email.com",
    "nickname": "Coco",
    "id": 4
  },
  "playerOneScore": 11,
  "playerTwoScore": 7
}]
