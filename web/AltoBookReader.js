br = new BookReader();

br.getPageWidth = function(index) {
	return this.pageW[index];
}

br.getPageHeight = function(index) {
	return this.pageH[index];
}

// reduce defaults to 1 (no reduction)
// rotate defaults to 0 (no rotation)
br.getPageURI = function(index, reduce, rotate) {
	var url;
	if ('undefined' != fileArray[index] && fileArray.length > 0) {
		url = filePath + fileArray[index];
	} else {
		var leafStr = '0000';
		var imgStr = this.leafMap[index].toString();
		var re = new RegExp("0{" + imgStr.length + "}$");
		url = filePath + leafStr.replace(re, imgStr) + "." + extension;
	}
	return url;
}

br.getPageSide = function(index) {
	// assume the book starts with a cover (right-hand leaf)
	// we should really get handside from scandata.xml

	// $$$ we should get this from scandata instead of assuming the accessible
	// leafs are contiguous
	if ('rl' != this.pageProgression) {
		// If pageProgression is not set RTL we assume it is LTR
		if (0 == (index & 0x1)) {
			// Even-numbered page
			return 'R';
		} else {
			// Odd-numbered page
			return 'L';
		}
	} else {
		// RTL
		if (0 == (index & 0x1)) {
			return 'L';
		} else {
			return 'R';
		}
	}
}

br.getPageNum = function(index) {
	var pageNum = this.pageNums[index];
	if (pageNum) {
		return pageNum;
	} else {
		return 'n' + index;
	}
}

// Single images in the Internet Archive scandata.xml metadata are (somewhat
// incorrectly)
// given a "leaf" number. Some of these images from the scanning process should
// not
// be displayed in the BookReader (for example colour calibration cards). Since
// some
// of the scanned images will not be displayed in the BookReader (those marked
// with
// addToAccessFormats false in the scandata.xml) leaf numbers and BookReader
// page
// indexes are generally not the same. This function returns the BookReader page
// index given a scanned leaf number.
//
// This function is used, for example, to map between search results (that use
// the
// leaf numbers) and the displayed pages in the BookReader.
br.leafNumToIndex = function(leafNum) {
	for ( var index = 0; index < this.leafMap.length; index++) {
		if (this.leafMap[index] == leafNum) {
			return index;
		}
	}

	return null;
}

// This function returns the left and right indices for the user-visible
// spread that contains the given index. The return values may be
// null if there is no facing page or the index is invalid.
br.getSpreadIndices = function(pindex) {
	// $$$ we could make a separate function for the RTL case and
	// only bind it if necessary instead of always checking
	// $$$ we currently assume there are no gaps

	var spreadIndices = [ null, null ];
	if ('rl' == this.pageProgression) {
		// Right to Left
		if (this.getPageSide(pindex) == 'R') {
			spreadIndices[1] = pindex;
			spreadIndices[0] = pindex + 1;
		} else {
			// Given index was LHS
			spreadIndices[0] = pindex;
			spreadIndices[1] = pindex - 1;
		}
	} else {
		// Left to right
		if (this.getPageSide(pindex) == 'L') {
			spreadIndices[0] = pindex;
			spreadIndices[1] = pindex + 1;
		} else {
			// Given index was RHS
			spreadIndices[1] = pindex;
			spreadIndices[0] = pindex - 1;
		}
	}

	// console.log(" index %d mapped to spread %d,%d", pindex, spreadIndices[0],
	// spreadIndices[1]);

	return spreadIndices;
}

// Returns the embed code HTML fragment suitable for copy and paste
br.getEmbedCode = function(frameWidth, frameHeight, viewParams) {
	return "Embed code not supported in bookreader demo.";
}

br.titleLeaf = firstPage;

br.pageW = pageWight;

br.pageH = pageHight;

br.leafMap = leafMap;

br.pageNums = pageNumbers;

br.numLeafs = br.pageW.length;

br.subPrefix = 'birdbookillustra00reedrich';

br.bookId = 'birdbookillustra00reedrich';

br.zip = linkToFile;

br.bookTitle = bookTitle;

br.bookUrl = '';

br.imageFormat = 'jp2';

br.pageProgression = pageProgression;

br.init();
// need to remove
br.bookPath = '/4/items/birdbookillustra00reedrich/birdbookillustra00reedrich';

br.server = 'ia700200.us.archive.org';

$('#BRtoolbar').find('.read').hide();
$('#BRtoolbar').find('.share').hide();
$('#BRtoolbar').find('.info').hide();
$('#BRtoolbar').find('.logo').hide();
$('#BRtoolbar').find('#BRreturn').hide();
if (hideSearch === "yes") {
	$('#BRtoolbar').find('#textSrch').hide();
	$('#BRtoolbar').find('#btnSrch').hide();
}
