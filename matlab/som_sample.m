%read data for SOM network
D = convert_to_som_pak_format('H:\TCC\Base de dados normalizada (por posição)\Base de dados (representação - coordenadas)\TCC\Alex.txt', 2, ['Abcissa', 'Ordinate']);
%create, initialize and train a SOM
sM = som_make(D);
%show data from the SOM
som_show(sM)