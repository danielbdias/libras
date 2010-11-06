function [qe, te, somMap] = execute_som(file_path, representation_size, representation_labels, somRoughTrainingParams, somFineTuningTrainingParams, som_size, umatrix_title)
% EXECUTE_SOM - initialize, train and plot a SOM based in data of a 
% representation file

generate_umatrix = 0;

% Check to see if no input arguments were supplied.  If this is the case,
% stop execution and output an error message to the user.
if nargin < 6
    error('MATLAB:execute_som:NrInputArguments', 'No input arguments were supplied. At least six is expected.');
elseif nargin <= 6 || nargin <=7
    if ~ischar(file_path)
        error('MATLAB:execute_som:InvalidInputArgument', 'The first input variable must be a valid character array.');
    end
    if strcmp(file_path, '') == 1
        error('MATLAB:execute_som:InvalidInputArgument', 'The first input variable cannot be empty.');
    end
    
    if ~isnumeric(representation_size)
        error('MATLAB:execute_som:InvalidInputArgument', 'The second input variable must be a valid number.');
    end
    if representation_size <= 0
        error('MATLAB:execute_som:InvalidInputArgument', 'The second input variable must be greater than 0 (zero).');
    end    

    if nargin > 6
        generate_matrix = 1;
        if ~ischar(umatrix_title)
            error('MATLAB:execute_som:InvalidInputArgument', 'The fifth input variable must be a valid character array.');
        end
        if strcmp(umatrix_title, '') == 1
            error('MATLAB:execute_som:InvalidInputArgument', 'The fifth input variable cannot be empty.');
        end
    end
    
elseif nargin > 7
    error('MATLAB:execute_som:TooManyInputArguments', 'Too many input arguments were supplied. The maximum permitted is seven.');
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

if (generate_umatrix == 1)
    %Plot the U-Matrix
    colormap('gray');
    som_show(somMap, 'umat', 'all');
    som_show_add('label', somMap.labels, 'textcolor', 'r');
    title(umatrix_title);
end

%figure(2);
%[color,b]=som_kmeanscolor(somMap,15);
%som_show(somMap,'color',color,'color',color(:,:,b));

%Measure error
[qe,te] = som_quality(somMap, somData);