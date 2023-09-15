import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QuoteFavoriteComponent } from './quote-favorite.component';

describe('QuoteFavoriteComponent', () => {
  let component: QuoteFavoriteComponent;
  let fixture: ComponentFixture<QuoteFavoriteComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [QuoteFavoriteComponent]
    });
    fixture = TestBed.createComponent(QuoteFavoriteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
