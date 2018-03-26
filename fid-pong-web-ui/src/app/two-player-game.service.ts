import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {TwoPlayerGame} from "../model";
import {Observable} from "rxjs/Observable";

@Injectable()
export class TwoPlayerGameService {

  twoPlayerGamesUrl = '/api/games/twoPlayer/'

  constructor(private http: HttpClient) { }

  getTwoPlayerGames(): Observable<TwoPlayerGame> {
    return this.http.get<TwoPlayerGame>(this.twoPlayerGamesUrl)
  }

}
