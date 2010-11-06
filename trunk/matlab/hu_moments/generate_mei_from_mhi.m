%% generate the MEI from a directory
function mei = generate_mei_from_mhi(mhi)

mei = mhi;

for i = 1:size(mei, 1)
    for j = 1:size(mei, 2)
        if mei(i, j) ~= 0
            mei(i, j) = 1;
        end
    end
end