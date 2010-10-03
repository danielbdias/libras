clear
clc

% threshold = 10;
% threshold = 100;
threshold = 1000;

x = -threshold:threshold;
y = x.^3;
% y = x.^3+x.^2;
% y = x.^3+x.^2+x;

% plot(x, y);
% title('Função de Terceiro Grau');

z = fft(x + 1i*y);

realComponent = real(z);
imaginaryComponent = imag(z);

plot(realComponent, imaginaryComponent);
title('Função de Terceiro Grau / Transformada de Fourier');