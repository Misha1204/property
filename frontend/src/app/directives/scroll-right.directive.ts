import { Directive, ElementRef, HostListener, Renderer2 } from '@angular/core';

@Directive({ selector: '[scrollRight]' })
export class ScrollRightDirective {
  constructor(private elRef: ElementRef, private renderer: Renderer2) {}

  @HostListener('click') scrollRight() {
    const scrollWidth = this.elRef.nativeElement.parentElement.scrollWidth;

    this.elRef.nativeElement.parentElement.scrollLeft += scrollWidth / 3.3333;
  }
}
