common_parameters;

%%%%%%%%%%%%%%%%%%% angulo_abcissa_ordenada_velocidade_angular %%%%%%%%%%%%%%%%%%%

file_path = [root_dir 'angulo_abcissa_ordenada_velocidade_angular.data'];
representation_size = 3;
representation_labels = ['Abcissa', 'Ordenada', 'Velocidade Angular'];
somRoughTrainingParams = [ 5, 1.25, 35 ];
somFineTuningTrainingParams = [ 1.25, 1, 137 ];
umatrix_title = 'Ângulo Abcissa, Ângulo Ordenada, Velocidade Angular';

[qe, te] = execute_som(file_path, representation_size, representation_labels, somRoughTrainingParams, somFineTuningTrainingParams, som_size, umatrix_title);

%Generate the analysis data
fid = fopen(csv_file, 'a');

if fid > 0
    fprintf(fid, '%s\r\n', [normalization_type ';angulo_abcissa_ordenada_velocidade_angular;' num2str(qe) ';' num2str(te) ';']);
    fclose(fid);
end;
