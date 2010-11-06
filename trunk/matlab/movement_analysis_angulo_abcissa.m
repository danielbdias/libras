common_parameters;

%%%%%%%%%%%%%%%%%%% angulo_abcissa %%%%%%%%%%%%%%%%%%%

file_path = [root_dir 'angulo_abcissa.data'];
representation_size = 1;
representation_labels = ['Abcissa'];
somRoughTrainingParams = [ 5, 1.25, 35 ];
somFineTuningTrainingParams = [ 1.25, 1, 137 ];
umatrix_title = '�ngulo Abcissa';

[qe, te] = execute_som(file_path, representation_size, representation_labels, somRoughTrainingParams, somFineTuningTrainingParams, som_size, umatrix_title);

%Generate the analysis data
fid = fopen(csv_file, 'a');

if fid > 0
    fprintf(fid, '%s\r\n', [normalization_type ';angulo_abcissa;' num2str(qe) ';' num2str(te) ';']);
    fclose(fid);
end;
