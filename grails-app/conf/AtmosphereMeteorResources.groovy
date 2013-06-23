modules = {
	'atmosphere-meteor' {
		dependsOn 'jquery'
		resource id: 'js',
				// Use one of the lines below to determine which JavaScript file to use.
				url: [plugin: 'atmosphere-meteor', dir: 'js', file: "atmosphere.js"],
				//url: [plugin: 'atmosphere-meteor', dir: 'js', file: "jquery.atmosphere.js"],
				disposition: 'head', nominify: true
	}
}