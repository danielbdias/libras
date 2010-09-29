function execute_som(file_path, representation_size, representation_labels, somRoughTrainingParams, somFineTuningTrainingParams, som_size, umatrix_title)
% EXECUTE_SOM - initialize, train and plot a SOM based in data of a 
% representation file
%
% Check to see if no input arguments were supplied.  If this is the case,
% stop execution and output an error message to the user.
if nargin < 7
    error('MATLAB:convert_to_som_pak_format:NrInputArguments', 'No input arguments were supplied. At least five is expected.');
elseif nargin <= 7
    if ~ischar(file_path)
        error('MATLAB:get_file_data:InvalidInputArgument', 'The first input variable must be a valid character array.');
    end
    if strcmp(file_path, '') == 1
        error('MATLAB:get_file_data:InvalidInputArgument', 'The first input variable cannot be empty.');
    end
    
    if ~isnumeric(representation_size)
        error('MATLAB:get_file_data:InvalidInputArgument', 'The second input variable must be a valid number.');
    end
    if representation_size <= 0
        error('MATLAB:get_file_data:InvalidInputArgument', 'The second input variable must be greater than 0 (zero).');
    end    
    
    if representation_size == length(representation_labels)
        error('MATLAB:get_file_data:InvalidInputArgument', 'The second input variable must be equal to the size of the third argument.');
    end
    
    if ~ischar(umatrix_title)
        error('MATLAB:get_file_data:InvalidInputArgument', 'The fifth input variable must be a valid character array.');
    end
    if strcmp(umatrix_title, '') == 1
        error('MATLAB:get_file_data:InvalidInputArgument', 'The fifth input variable cannot be empty.');
    end
    
elseif nargin > 5
    error('MATLAB:get_file_data:TooManyInputArguments', 'Too many input arguments were supplied. The maximum permitted is five.');
end

%Get data struct from representation file
somData = convert_to_som_pak_format(file_path, representation_size, representation_labels);

%Define topology for SOM
somTopology = som_topol_struct('lattice', 'hexa');

%Initialize the SOM using the linear initialization
somMap = som_lininit(somData, 'topol', somTopology, 'data', somData, 'msize', som_size);

%Define the training parameters
somRoughTraining = som_train_struct('map', somMap, 'algorithm', 'batch', 'phase', 'rough');
somRoughTraining.neigh = 'gaussian';
somRoughTraining.radius_ini = somRoughTrainingParams(1);
somRoughTraining.radius_fin = somRoughTrainingParams(2);
somRoughTraining.trainlen = somRoughTrainingParams(3);

somFineTuningTraining = som_train_struct('phase', 'finetuning', 'previous', somRoughTraining);
somFineTuningTraining.neigh = 'gaussian';
somFineTuningTraining.radius_ini = somFineTuningTrainingParams(1);
somFineTuningTraining.radius_fin = somFineTuningTrainingParams(2);
somFineTuningTraining.trainlen = somFineTuningTrainingParams(3);

%Train the SOM
somMap = som_batchtrain(somMap, somData, 'train', somFineTuningTraining);

%Label the map
somMap = som_autolabel(somMap, somData, 'vote');

%Plot the U-Matrix
som_show(somMap, 'umat', 'all');
som_show_add('label', somMap.labels);
title(umatrix_title);
