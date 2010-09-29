common_parameters;

%%%%%%%%%%%%%%%%%%% angulo_abcissa_ordenada %%%%%%%%%%%%%%%%%%%

file_path = [root_dir 'angulo_abcissa_ordenada.data'];
representation_size = 2;
representation_labels = ['Abcissa', 'Ordenada'];
somRoughTrainingParams = [ 5, 1.25, 35 ];
somFineTuningTrainingParams = [ 1.25, 1, 137 ];
umatrix_title = 'Ângulo Abcissa, Ângulo Ordenada';

execute_som(file_path, representation_size, representation_labels, somRoughTrainingParams, somFineTuningTrainingParams, som_size, umatrix_title);