import { TestBed, async, inject } from '@angular/core/testing';

import { ArticleGuard } from './article.guard';

describe('ArticleGuard', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ArticleGuard]
    });
  });

  it('should ...', inject([ArticleGuard], (guard: ArticleGuard) => {
    expect(guard).toBeTruthy();
  }));
});
