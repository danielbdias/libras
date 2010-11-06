function somDataStruct = convert_to_som_pak_format(file_path, representation_size, representation_labels)
% CONVERT_TO_SOM_PAK_FORMAT	- convert the data from a libras file to the 
% SOM_PAK struct
%
% Check to see if no input arguments were supplied.  If this is the case,
% stop execution and output an error message to the user.
if nargin < 3
    error('MATLAB:convert_to_som_pak_format:NrInputArguments', 'No input arguments were supplied. At least three is expected.');
elseif nargin <= 3
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
    
elseif nargin > 3
    error('MATLAB:get_file_data:TooManyInputArguments', 'Too many input arguments were supplied. The maximum permitted is three.');
end

%get file data with normalization
file_data = get_file_data(file_path, representation_size);

%create component names for SOM_PAK data file
cnames = cell(size(file_data, 2) - 1, 1);

for i = 1:representation_size
    for j = 1:representation_size:length(cnames)
        cnames(j) = cellstr([char(representation_labels(i)) num2str((j+representation_size-i)/representation_size)]);
    end    
end

somDataStruct = som_data_struct(file_data(:,1:end-1), 'comp_names', cnames);

for line = 1:size(file_data, 1)
    somDataStruct = som_label(somDataStruct,'add', line, num2str(file_data(line, end)));
end