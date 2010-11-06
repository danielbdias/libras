common_parameters;

%%%%%%%%%%%%%%%%%%% coordenadas_angulo_abcissa_ordenada %%%%%%%%%%%%%%%%%%%

file_path = [root_dir 'coordenadas_angulo_abcissa_ordenada.data'];
representation_size = 4;
representation_labels = ['Abcissa', 'Ordenada', 'Ângulo Abcissa', 'Ângulo Ordenada'];
somRoughTrainingParams = [ 5, 1.25, 35 ];
somFineTuningTrainingParams = [ 1.25, 1, 137 ];
umatrix_title = 'Coordenadas, Ângulo Abcissa, Ângulo Ordenada';

[qe, te] = execute_som(file_path, representation_size, representation_labels, somRoughTrainingParams, somFineTuningTrainingParams, som_size, umatrix_title);

%Generate the analysis data
fid = fopen(csv_file, 'a');

if fid > 0
    fprintf(fid, '%s\r\n', [normalization_type ';coordenadas_angulo_abcissa_ordenada;' num2str(qe) ';' num2str(te) ';']);
    fclose(fid);
end;