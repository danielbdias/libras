common_parameters;

%%%%%%%%%%%%%%%%%%% angulo_abcissa %%%%%%%%%%%%%%%%%%%

file_path = [root_dir 'angulo_abcissa.data'];
representation_size = 1;
representation_labels = ['Abcissa'];
somRoughTrainingParams = [ 5, 1.25, 35 ];
somFineTuningTrainingParams = [ 1.25, 1, 137 ];
umatrix_title = 'Ângulo Abcissa';

execute_som(file_path, representation_size, representation_labels, somRoughTrainingParams, somFineTuningTrainingParams, som_size, umatrix_title);