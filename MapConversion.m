function MapConversion(x,y)
convX=@(x) x+(113-130);
convY=@(y) y+ (289-239);


fprintf(['this._mapX=' num2str(convX(x)) ';\n']);
fprintf(['this._mapY=' num2str(convY(y)) ';\n']);




end


