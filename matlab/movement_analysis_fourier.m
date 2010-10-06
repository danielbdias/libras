common_parameters;

%%%%%%%%%%%%%%%%%%% angulo_abcissa %%%%%%%%%%%%%%%%%%%

file_path = [root_dir 'fourier_normalized.data'];
representation_size = 2;
representation_labels = ['Abcissa', 'Ordinate'];
somRoughTrainingParams = [ 5, 1.25, 35 ];
somFineTuningTrainingParams = [ 1.25, 1, 137 ];
umatrix_title = 'Fourier';

execute_som(file_path, representation_size, representation_labels, somRoughTrainingParams, somFineTuningTrainingParams, som_size, umatrix_title);