database_dirs = { 
    'D:\Development\Eclipse Workspaces\Academic\IC\databases\normalized by dimension\',
	'D:\Development\Eclipse Workspaces\Academic\IC\databases\normalized by dimension and position\',
    'D:\Development\Eclipse Workspaces\Academic\IC\databases\normalized by data\'};

database_files = {'angulo_abcissa.data', 1, ['Abcissa'];
                 'angulo_abcissa_ordenada.data', 2, ['Abcissa', 'Ordenada'];
                 'angulo_abcissa_ordenada_velocidade_angular.data', 3, ['Abcissa', 'Ordenada', 'Velocidade Angular'];
                 'angulo_abcissa_velocidade_angular.data', 2, ['Abcissa', 'Velocidade Angular'];
                 'coordenadas.data', 2, ['Abcissa', 'Ordenada'];
                 'coordenadas_angulo_abcissa.data', 3, ['Abcissa', 'Ordenada', 'Ângulo Abcissa'];
                 'coordenadas_angulo_abcissa_ordenada.data', 4, ['Abcissa', 'Ordenada', 'Ângulo Abcissa', 'Ângulo Ordenada'];
                 'coordenadas_velocidade_angular.data', 3, ['Abcissa', 'Ordenada', 'Velocidade Angular'];
                 'coordenadas_velocidade_angular_instantanea.data', 4, ['Abcissa', 'Ordenada', 'Velocidade Angular', 'Velocidade Instantânea'];
                 'coordenadas_velocidade_instantanea.data', 3, ['Abcissa', 'Ordenada', 'Velocidade Instantânea'];
                 'fourier_normalized.data', 2, ['Abcissa', 'Ordenada']};

normalizations = {'normalized_by_dimension', 'normalized_by_dimension_and_position', 'normalized_by_data'};

bmuFileDir = 'D:\Development\Eclipse Workspaces\Academic\IC\batchs\';

somRoughTrainingParams = [ 5, 1.25, 35 ];
somFineTuningTrainingParams = [ 1.25, 1, 137 ];
somSize = [ 15, 15 ];

for i = 1:size(database_dirs, 1)
    for j = 1: size(database_files, 1)
        file_path = strcat(database_dirs{i}, database_files{j, 1});
        representation_size = database_files{j, 2};
        representation_labels = database_files{j, 3};
        bmu_file = [bmuFileDir 'lvqNetworkSetup_' normalizations{i} '_' strrep(database_files{j, 1}, '.data', '.txt') ];
        
        [qe, te, somMap] = execute_som(file_path, representation_size, representation_labels, somRoughTrainingParams, somFineTuningTrainingParams, somSize);
        
        generate_bmu_file(somMap, bmu_file);
    end
end