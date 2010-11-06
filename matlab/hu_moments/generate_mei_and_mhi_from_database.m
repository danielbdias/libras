read_dir = 'H:\TCC\Base de dados (imagens segmentadas)';
mei_dir = 'H:\TCC\Base de dados (MEI)';
mhi_dir = 'H:\TCC\Base de dados (MHI)';

base_dirs = {%'\IC\Daniel\',
             '\IC\Roberta\',
             '\IC\Sara\',
             '\IC\Videos - Sara\',
             '\IC\Videos - todos\',
             '\TCC\Alex\',
             '\TCC\Daniel\',
             '\TCC\Lucas\',
             '\TCC\Márcia\',
             '\TCC\Mirian\'};
         
for i = 1:size(base_dirs, 1)
    base_dir = [read_dir base_dirs{i}];
    display(['Processing dir: ' base_dir]);
    
    movement_dirs = dir(base_dir);
    
    for j = 1:size(movement_dirs, 1)
        movement_dir_name = movement_dirs(j).name;
        if strcmp(movement_dir_name, '.') ~= 1 && strcmp(movement_dir_name, '..') ~= 1
            display(['Generating MEI and MHI for: ' movement_dir_name]);
            
            mei_file = [mei_dir base_dirs{i} movement_dir_name '.jpg'];
            mhi_file = [mhi_dir base_dirs{i} movement_dir_name '.jpg'];
            
            if exist(mei_file, 'file') == 0 && exist(mhi_file, 'file') == 0
                mhi = generate_mhi([base_dir movement_dir_name]);
                mei = generate_mei_from_mhi(mhi);
                
                imwrite(mei, mei_file, 'jpg');
                imwrite(mhi, mhi_file, 'jpg');
                
                display('MEI and MHI generated.');
            else
                display('MEI and MHI already exists.');
            end
        end
    end
end