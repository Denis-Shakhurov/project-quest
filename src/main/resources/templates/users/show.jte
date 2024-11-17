@import dto.UserPage
@param UserPage page

@template.layout.page(
content = @`
    <section>
        <div class="container-lg mt-5">
            <h1>${page.getUser().getName()}, выбери игру.</h1>
            <form action="/users/${page.getUser().getId()}" method="post" name="CalcGame" class="rss-form text-body">
                <div class="row">
            <div class="col">
                <button type="submit" name="CalcGame" class="h-100 btn btn-lg btn-primary px-sm-3">CalcGame</button>
            </div>
                </div>
            </form>
        </div>
    </section>
`
)