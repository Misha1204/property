import { Directive, ElementRef, HostListener, Renderer2 } from '@angular/core';

@Directive({ selector: '[scrollLeft]' })
export class ScrollLeftDirective {
  constructor(private elRef: ElementRef, private renderer: Renderer2) {}

  @HostListener('click') scrollRight() {
    this.elRef.nativeElement.parentElement.scrollLeft -=
      this.elRef.nativeElement.parentElement.scrollWidth / 3.3333;
  }
}
