common_parameters;

%%%%%%%%%%%%%%%%%%% coordenadas_angulo_abcissa_ordenada %%%%%%%%%%%%%%%%%%%

file_path = [root_dir 'coordenadas_angulo_abcissa_ordenada.data'];
representation_size = 4;
representation_labels = ['Abcissa', 'Ordenada', '�ngulo Abcissa', '�ngulo Ordenada'];
somRoughTrainingParams = [ 5, 1.25, 35 ];
somFineTuningTrainingParams = [ 1.25, 1, 137 ];
umatrix_title = 'Coordenadas, �ngulo Abcissa, �ngulo Ordenada';

execute_som(file_path, representation_size, representation_labels, somRoughTrainingParams, somFineTuningTrainingParams, som_size, umatrix_title);