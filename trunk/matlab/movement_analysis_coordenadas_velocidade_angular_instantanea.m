common_parameters;

%%%%%%%%%%%%%%%%%%% coordenadas_velocidade_angular_instantanea %%%%%%%%%%%%%%%%%%%

file_path = [root_dir 'coordenadas_velocidade_angular_instantanea.data'];
representation_size = 4;
representation_labels = ['Abcissa', 'Ordenada', 'Velocidade Angular', 'Velocidade Instantânea'];
somRoughTrainingParams = [ 5, 1.25, 35 ];
somFineTuningTrainingParams = [ 1.25, 1, 137 ];
umatrix_title = 'Coordenadas, Velocidade Angular, Velocidade Instantânea';

execute_som(file_path, representation_size, representation_labels, somRoughTrainingParams, somFineTuningTrainingParams, som_size, umatrix_title);