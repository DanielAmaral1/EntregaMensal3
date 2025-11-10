import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AgendamentosComponent } from './agendamentos';

describe('AgendamentosComponent', () => {
  let component: AgendamentosComponent;
  let fixture: ComponentFixture<AgendamentosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AgendamentosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AgendamentosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
