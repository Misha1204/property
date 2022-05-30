import { Directive, ElementRef, HostListener, Renderer2 } from '@angular/core';

@Directive({ selector: '[extend]' })
export class ExtendDirective {
  constructor(private elRef: ElementRef, private renderer: Renderer2) {}

  @HostListener('click') extend() {
    const element = this.elRef.nativeElement.parentElement as HTMLElement;
    // const scrollWidth = this.elRef.nativeElement.parentElement.scrollWidth;

    // this.elRef.nativeElement.parentElement.scrollLeft += scrollWidth / 3.3333;
    // element.heig = '150px';
    console.log(this.elRef.nativeElement.parentElement.offsetHeight);
    this.elRef.nativeElement.parentElement.offsetHeight = 150;
  }
}
