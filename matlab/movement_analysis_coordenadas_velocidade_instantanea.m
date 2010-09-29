common_parameters;

%%%%%%%%%%%%%%%%%%% coordenadas_velocidade_instantanea %%%%%%%%%%%%%%%%%%%

file_path = [root_dir 'coordenadas_velocidade_instantanea.data'];
representation_size = 3;
representation_labels = ['Abcissa', 'Ordenada', 'Velocidade Instantânea'];
somRoughTrainingParams = [ 5, 1.25, 35 ];
somFineTuningTrainingParams = [ 1.25, 1, 137 ];
umatrix_title = 'Coordenadas, Velocidade Instantânea';

execute_som(file_path, representation_size, representation_labels, somRoughTrainingParams, somFineTuningTrainingParams, som_size, umatrix_title);