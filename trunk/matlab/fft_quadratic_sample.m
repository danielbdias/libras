clear
clc

% threshold = 10;
% threshold = 100;
threshold = 1000;

x = -threshold:threshold;
% y = x.^2;
% y = x.^2+x;
y = x.^2+5*x+10;

% plot(x, y);
% title('Função Quadrátrica');

z = fft(x + 1i*y);

realComponent = real(z);
imaginaryComponent = imag(z);

plot(realComponent, imaginaryComponent);
title('Função Quadrática / Transformada de Fourier');