clear
clc

threshold = 10;
%threshold = 100;
%threshold = 1000;

x = -threshold:threshold;
%y = 2*x;
%y = 2*x+5;
y = x;

%plot(x, y);
%title('Fun��o Linear');

z = fft(x + 1i*y);

realComponent = real(z);
imaginaryComponent = imag(z);

plot(realComponent, imaginaryComponent);
title('Fun��o Linear / Transformada de Fourier');