clear
clc

% threshold = 10;
% threshold = 100;
threshold = 1000;

x = -threshold:threshold;
% y = sin(2*x);
% y = cos(2*x+1);
y = tan(2*x);

% plot(x, y);
% title('Função Trigonométrica');

z = fft(x + 1i*y);

realComponent = real(z);
imaginaryComponent = imag(z);

plot(realComponent, imaginaryComponent);
title('Função Trigonométrica / Transformada de Fourier');