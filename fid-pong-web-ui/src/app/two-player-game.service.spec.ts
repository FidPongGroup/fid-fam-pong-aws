import { TestBed, inject } from '@angular/core/testing';

import { TwoPlayerGameServiceService } from './two-player-game.service';

describe('TwoPlayerGameServiceService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [TwoPlayerGameServiceService]
    });
  });

  it('should be created', inject([TwoPlayerGameServiceService], (service: TwoPlayerGameServiceService) => {
    expect(service).toBeTruthy();
  }));
});
