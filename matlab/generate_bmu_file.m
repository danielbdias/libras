function generate_bmu_file(somMap, file)

%Generate the analysis data
fid = fopen(file, 'w');

if fid > 0
    
    for i = 1:size(somMap.labels)
        label = somMap.labels{i, 1};
        
        if strcmp(label, '') == 0
            for j = 1:size(somMap.codebook, 2)
                fprintf(fid, '%f,', somMap.codebook(i, j));
            end
        
            fprintf(fid, '%s\r\n', label);
        end
    end
    
    fclose(fid);
end;

